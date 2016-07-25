package com.schoolshieldchild.model.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId The student_id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }


}
