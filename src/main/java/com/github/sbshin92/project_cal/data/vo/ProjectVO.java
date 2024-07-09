package com.github.sbshin92.project_cal.data.vo;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectVO {
	 private int projectId;
    private int userId;
    private String projectTitle;
    private String projectDescription;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String projectStatus;
}