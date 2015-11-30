package edu.sjsu.cmpe275.project.controller;

import edu.sjsu.cmpe275.project.exception.BadRequestException;
import edu.sjsu.cmpe275.project.model.Pages;
import edu.sjsu.cmpe275.project.model.Report;
import edu.sjsu.cmpe275.project.model.Task;
import edu.sjsu.cmpe275.project.model.User;
import edu.sjsu.cmpe275.project.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @RequestMapping(value = "/add_task", method = RequestMethod.GET)
    public String showAddTask(Task task, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        return Pages.addtask.toString();
    }

    @RequestMapping(value = "/add_task", method = RequestMethod.POST)
    public String addTask(
            @ModelAttribute(value = "task") Task task, HttpServletRequest request, Model model) {
        try {
            if (!task.getTitle().isEmpty() && !task.getDescription().isEmpty()) {
                session = request.getSession();
                task.setState(Task.TaskState.NEW);
                task.setAssignee((User) session.getAttribute(userSession));
                long taskId = taskService.createTask(task);
                session.setAttribute("taskId", taskId);
                logger.info(request.getRequestURL() + ": " + "Task created of id " + taskId);
                return Pages.addtask.toString();
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

    @RequestMapping(value = "/{task_id}/update_task", method = RequestMethod.GET)
    public String showUpdateTask(@PathVariable("task_id") String taskId, Task task, HttpServletRequest request) {
        logger.info("Redirecting to " + request.getRequestURL());
        return Pages.updatetask.toString();
    }

    @RequestMapping(value = "/{task_id}/update_task", method = RequestMethod.POST)
    public String updateTask(@PathVariable("task_id") String taskId,
                             @ModelAttribute(value = "task") Task task, HttpServletRequest request, Model model) {
        try {
            if (task != null) {
                task.setId(Long.valueOf(taskId));
                boolean status = taskService.editTask(task);
                if (status) {
                    logger.info(request.getRequestURL() + ": " + "Task updated of id " + taskId);
                    return Pages.updatetask.toString();
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

    @RequestMapping(value = "/{task_id}/remove_task", method = RequestMethod.GET)
    public String showDeleteTask(@PathVariable("task_id") String taskId, HttpServletRequest request) {
        try {
            boolean status = taskService.removeTaskById(Long.valueOf(taskId));
            if (status) {
                logger.info(request.getRequestURL() + ": " + "Task deleted of id " + taskId);
                return Pages.task.toString();
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

    @RequestMapping(value = "/project_report", method = RequestMethod.GET)
    public String getProjectReport(@RequestParam("id") String projectId, HttpServletRequest request, Model model) {
        try {
            session = request.getSession();
            if (session == null)
                throw new IllegalStateException("Session doesn't exist");
            User user = (User) session.getAttribute(userSession);
            if (user != null) {
                long countUnfinishedTaskByProject = taskService.countUnfinishedTaskByProject(Long.valueOf(projectId));
                long countFinishedTaskByProject = taskService.countFinishedTaskByProject(Long.valueOf(projectId));
                long countAllTaskByProject = taskService.countAllTaskByProject(Long.valueOf(projectId));
                long countAllCancelledTaskByProject = taskService.countAllCancelledTaskByProject(Long.valueOf(projectId));
                List<Report> countUserFinishedTask = taskService.getFinishedTaskOfEachUser(Long.valueOf(projectId));
                model.addAttribute("unfinishedTaskCount", countUnfinishedTaskByProject);
                model.addAttribute("finishedTaskCount", countFinishedTaskByProject);
                model.addAttribute("allTaskCount", countAllTaskByProject);
                model.addAttribute("cancelledTaskCount", countAllCancelledTaskByProject);
                model.addAttribute("userFinishedTask", countUserFinishedTask);
                logger.info(request.getRequestURL() + ": Tasks count returned for project id " + projectId);
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
