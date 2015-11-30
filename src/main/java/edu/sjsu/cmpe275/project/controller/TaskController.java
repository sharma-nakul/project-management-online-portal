package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.Pages;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.ITaskService;
import org.slf4j.Logger;
import edu.sjsu.cmpe275.project.model.Report;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import edu.sjsu.cmpe275.project.model.Project;

/**
 * @author Naks
 *         Controller class to handle User APIs.
 */
@Controller
public class TaskController {

    /**
     * Variable of type logger to print data on console
     */
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private static final String userSession = "userDetails";

    @Autowired
    ITaskService taskService;
    private HttpSession session;

    @RequestMapping(value = "/addtask", method = RequestMethod.GET)
    public String showAddTask(@RequestParam("id") String id, Project project, Task task, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        return Pages.addtask.toString();
    }

    @RequestMapping(value = "/addtask", method = RequestMethod.POST)
    public String addTask(@RequestParam("id") String id,
            @ModelAttribute(value = "task") Task task, HttpServletRequest request, Model model) {
        try {
            if (!task.getTitle().isEmpty() && !task.getDescription().isEmpty()) {
                session = request.getSession();
                task.setState(Task.TaskState.NEW);
                task.setProject(taskService.getProject(Long.valueOf(id)));
                long taskId = taskService.createTask(task);
                session.setAttribute("taskId", taskId);
                logger.info(request.getRequestURL() + ": " + "Task created of id " + taskId);
                return "redirect:/" + Pages.viewproject.toString() + "?id=" + id;
            } else
                throw new BadRequestException("Required fields are missing", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("taskCreationError", true);
            model.addAttribute("badException", e);
            return Pages.addtask.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/updatetask", method = RequestMethod.GET)
    public String showUpdateTask(@RequestParam("id") String id, Task task, Model model, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        List<User> users = taskService.getParticipantList(taskService.getTaskById(Long.valueOf(id)).getProject().getId());
        users.add(taskService.getTaskById(Long.valueOf(id)).getProject().getOwner());
        model.addAttribute("users", users);
        model.addAttribute("task", taskService.getTaskById(Long.valueOf(id)));
        return Pages.updatetask.toString();
    }

    @RequestMapping(value = "/updatetask", method = RequestMethod.POST)
    public String updateTask(@RequestParam("id") String id,
                             @ModelAttribute(value = "task") Task task, HttpServletRequest request, Model model) {
        try {
            if (task != null) {
                task.setId(Long.valueOf(id));
                boolean status = taskService.editTask(task);
                if (status) {
                    logger.info(request.getRequestURL() + ": " + "Task updated of id " + id);
                    return "redirect:/" + Pages.viewproject.toString() + "?id=" + taskService.getTaskById(Long.valueOf(id)).getProject().getId();
                } else
                    throw new BadRequestException("Error updating task");
            } else
                throw new BadRequestException("Required fields are missing", HttpStatus.BAD_REQUEST.value(), "mandatory");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("taskUpdateError", true);
            model.addAttribute("badException", e);
            return Pages.updatetask.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            model.addAttribute("exception", e);
            return Pages.error.toString();
        }
    }

    /*@RequestMapping(value = "/{project_id}/task", method = RequestMethod.GET)
    public String getTasksByProjectId(@PathVariable("project_id") String projectId, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                List<Task> taskList = taskService.getTaskByProjectId(Long.valueOf(projectId));
                model.addAttribute("projectTaskList", taskList);
                logger.info(request.getRequestURL() + ": Task(s) list returned for project id " + projectId);
                return Pages.task.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    } */

    @RequestMapping(value = "/removetask/{id}", method = RequestMethod.POST)
    public String showDeleteTask(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            long projectId = taskService.getTaskById(Long.valueOf(id)).getProject().getId();
            boolean status = taskService.removeTaskById(Long.valueOf(id));
            if (status) {
                logger.info(request.getRequestURL() + ": " + "Task deleted of id " + id);
                return "redirect:/" + Pages.viewproject.toString() + "?id=" + projectId;
            } else
                throw new BadRequestException("Error deleting task");
        } catch (BadRequestException e) {
            logger.error("BadRequestException: " + request.getRequestURL() + ": " + e.getMessage());
            return Pages.task.toString();
        } catch (Exception e) {
            logger.error("Exception: " + request.getRequestURL() + ": " + e.getMessage());
            return Pages.error.toString();
        }
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String getProjectReport(@RequestParam("id") String id, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                long countUnfinishedTaskByProject = taskService.countUnfinishedTaskByProject(Long.valueOf(id));
                long countFinishedTaskByProject = taskService.countFinishedTaskByProject(Long.valueOf(id));
                long countAllTaskByProject = taskService.countAllTaskByProject(Long.valueOf(id));
                long countAllCancelledTaskByProject = taskService.countAllCancelledTaskByProject(Long.valueOf(id));
                List<Report> countUserFinishedTask = taskService.getFinishedTaskOfEachUser(Long.valueOf(id));
                model.addAttribute("unfinishedTaskCount", countUnfinishedTaskByProject);
                model.addAttribute("finishedTaskCount", countFinishedTaskByProject);
                model.addAttribute("allTaskCount", countAllTaskByProject);
                model.addAttribute("cancelledTaskCount", countAllCancelledTaskByProject);
                model.addAttribute("userFinishedTask", countUserFinishedTask);
                logger.info(request.getRequestURL() + ": Tasks count returned for project id " + id);
                return Pages.reports.toString();
            } else
                throw new IllegalStateException("Session doesn't exist");
        } catch (NullPointerException e) {
            logger.error("NullPointerException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        } catch (IllegalStateException e) {
            logger.error("IllegalStateException: " + request.getRequestURL() + ": " + e.getMessage());
            return "redirect:/" + Pages.login.toString();
        }
    }

}
