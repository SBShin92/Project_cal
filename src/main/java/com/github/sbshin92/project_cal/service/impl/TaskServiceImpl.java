package com.github.sbshin92.project_cal.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.sbshin92.project_cal.data.dao.TasksDAO;
import com.github.sbshin92.project_cal.data.vo.TaskVO;
import com.github.sbshin92.project_cal.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    private final TasksDAO tasksDAO;

    @Autowired
    public TaskServiceImpl(TasksDAO tasksDAO) {
        this.tasksDAO = tasksDAO;
    }
    
    //테스크 생성
    @Override
    @Transactional
    public int insert(TaskVO taskVO) {
        return tasksDAO.insert(taskVO);
    }

    //테스크 조회
    @Override
    public List<TaskVO> findAll() {
        return tasksDAO.findAll();
    }

    
    //테크스 삭제
    @Override
    @Transactional
    public int deleteTask(int taskId) {
        return tasksDAO.deleteTask(taskId);
    }

    //테스크 수정
    @Override
    @Transactional
    public int updateTask(TaskVO taskVO) {
    	return tasksDAO.updateTask(taskVO);
    }
    
    //veiw 내용 조회
    @Override
    public TaskVO findById(int taskId) {
        return tasksDAO.findById(taskId);
    }  
    
    
	//0725 기능 추가 getTasksByProjectId
	@Override
	 public List<TaskVO> getTasksByProjectId(Integer projectId,TaskVO taskVO) {
        if (projectId == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        } 
        
        //0725 페이지 세팅 기능 추가 
        int page = 0;
        int size = 10;
        if(taskVO.getPage() != 0 ) {
        	page = taskVO.getPage();
        }
        //0725 페이지 세팅 기능 추가 
		int offset = (page - 1) * size;
		
        return tasksDAO.getTasksByProjectId(projectId, new RowBounds(offset, size));
    }
	
	//0725 추가함
	@Override
	public int getTotalTasksCountByProjectId(int projectId) {
        
	    return tasksDAO.getTotalTasksCountByProjectId(projectId);
	}

	
	//0722 searchByTitle for search
	@Override
	public List<TaskVO> searchByTitle(TaskVO taskVO) {
		  	// 검색어가 null이거나 비어있는 경우 처리
		if (taskVO.getTaskTitle() == null || taskVO.getTaskTitle().trim().isEmpty()) {
			return List.of();	//빈리스트 반환 또는 다른 적절한 처리 
		}
			// 검색어 전처리 (옵션)
        String processedTitle = taskVO.getTaskTitle().trim(); // 앞뒤 공백 제거
	    taskVO.setTaskTitle(processedTitle);
	    
        //페이지 세팅
        int page = 0;
        int size = 10;
        if(taskVO.getPage() != 0 ) {
        	page = taskVO.getPage();
        }
        
		int offset = (page - 1) * size;
		
		return tasksDAO.searchByTitle(taskVO, new RowBounds(offset, size));
	}
	
	//0723
	@Override
	public int getTotalTasksCount(TaskVO taskVO) {

	  	// 검색어가 null이거나 비어있는 경우 처리
		if (taskVO.getTaskTitle() == null || taskVO.getTaskTitle().trim().isEmpty()) {
			return 0;	//빈리스트 반환 또는 다른 적절한 처리 
		}
			// 검색어 전처리 (옵션)
	    String processedTitle = taskVO.getTaskTitle().trim(); // 앞뒤 공백 제거
	    taskVO.setTaskTitle(processedTitle);
	    return tasksDAO.getTotalTasksCount(taskVO);
	}
}    
