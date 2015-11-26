# cmpe275-project
This is an academic project on Project Management which used technologies like Spring MVC, ORM, AOP, Hibernate, JMS and MySQL

#Project Manager  
This application provides task management for a group of people to get a project done. The major features include:  
User account creation. A user must use a valid email to sign up.   
####1. A user create a project, which makes him the owner of the project. A project has a title and description.  ####
####2. The owner of a project can invite other users to participate the project by sending email invitations.  ####
       The email recipient can click the link in the email to accept the invitation. It is also OK to provide the   
       invitation and invitation acceptance through the web UI itself; i.e., a user will be able to view the   
       invitations in the app, and accept as he chooses to.    
####3. Any participant of the project, including the owner, can add tasks to a project. Each task has title, description, assignee, state, estimate, and actual.  ####
    a. State is any of new, assigned, started, finished, and cancelled.  
    b. Only the owner of the project can cancel a task.    
    c. Estimate and actual are integer values, respectively represent estimated units of work  
       (provided when planning a task) and the actual units of work (provided when marking a task as finished).   
####4. A project is in one of the four states, planning, ongoing, cancelled, and completed.  ####
    a. When a project is just created, its status is planning.  
    b. Only during the planning stage, tasks can be added to or deleted from the project.  
    c. The owner can start a project when every task is in assigned state with an assignee, and have the  
       estimated units of work filled in. This moves the project from planning to the ongoing state.  
       Once a project is started, it cannot be moved back to the planning state.   
    d. When a project is ongoing:  
        i. The assignee of a task can still be changed until the task is marked as finished or cancelled.  
        ii. Estimates can no longer changed.  
        iii. New tasks can still be added to an ongoing project along with an estimate that cannot be changed later.  
        iv. The assignee of a new task can start the task, moving it to started state.   
        v. Tasks can no longer be deleted, but the project owner can cancel any task.  
        vi. When an assignee finishes a started task, he moves the task to finished state, along with the actual  
            units of work provided.  
    e. Only the owner can cancel a project that is in new, planning, or ongoing state.   
    f. Only the owner can move an ongoing project to completed state, if and only if (1) every task is in either  
       finished or cancelled state, and (2) there is at least one finished task.   
    g. The cancelled and completed states are considered terminal states. Once the project is in a terminal state,  
       its state cannot be changed any more, neither can anyone make any further change to any of its tasks.  
####5. The app must be able to report the progress of a project  ####
    a. Total number of task units finished and total number of tasks units to be finished  
    b. Total number of task units originally planned during the planning stage, and the total number of task units  
       cancelled so far.  
    c. Score board of team members sorted by the number of task units completed so far  
  
