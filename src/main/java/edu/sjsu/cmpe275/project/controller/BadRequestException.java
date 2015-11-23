package edu.sjsu.cmpe275.project.controller;

/**
 * @author Nakul Sharma
 * Exception handling class to handle user defined HTTP request exceptions.
 */
public class BadRequestException extends Throwable {

    /**
     * URL of request made.
     */
    private String url;

    /**
     * It holds user defined message when exception is thrown.
     */
    private String message;

    /**
     * It holds HTTP return code like 404, 200, 400, etc.
     */
    private int statusCode;

    /**
     * Constructor to assign url and message to thrown exception.
     * @param url URL of HttpServletRequest
     * @param message User defined value for the exception.
     */
    public BadRequestException(String url, String message) {
        this.url = url;
        this.message = message;
    }

    /**
     * Constructor to assign message and status code to thrown exception.
     * @param message User defined value for the exception.
     * @param statusCode It will have integer type HTTP error code like 404, 400, etc.
     */
    public BadRequestException(String message, int statusCode)
    {
        this.message=message;
        this.statusCode=statusCode;
    }

    /**
     * Constructor to assign message to thrown exception.
     * @param message User defined value for the exception.
     */
    public BadRequestException(String message){
        this.message=message;
    }

    /**
     * Getter for property - url.
     * @return HttpServletRequest URL of String datatype.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter for property - url
     * @param url HttpServletRequest URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter for property - message;
     * @return Exception message of String datatype.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for property - message.
     * @param message Exception message of String datatype.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for property - statusCode
     * @return status code of integer datatype.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Setter for property - statusCode
     * @param statusCode Integer datatype status code like 400, 404, etc.
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
