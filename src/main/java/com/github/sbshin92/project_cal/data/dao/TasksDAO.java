package com.github.sbshin92.project_cal.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import com.github.sbshin92.project_cal.data.vo.TaskVO;

//데이터베이스 연결: TasksDAO 메서드 내에서 데이터베이스 연결을 설정해야 합니다. 
// 이를 위해 MyBatis 설정 파일과 데이터베이스 연결 정보를 구성해야 합니다.

@Mapper
public interface TasksDAO {
	
	//테스크 생성
	//TaskVO 객체를 매개변수로 사용하며, 
	//이 객체에는 userId, projectId, taskTitle, taskDescription 등의 세부 정보가 포함
	@Insert("INSERT INTO tasks (user_id, project_id, task_title, task_description, task_status) " 
	        + "VALUES (#{userId}, #{projectId}, #{taskTitle}, #{taskDescription}, #{taskStatus})")
	public Integer insert(TaskVO taskVO);
	// => 서비스들려서 컨트롤러의 insert메서드로 가기

	//모든 task리스트들을 조회 ->tasks 테이블에서 모든 작업을 가져옵니다.
	@Select("SELECT task_id as taskId, "
			+ "user_id as userId, "
			+ "project_id as projectId, "
			+ "task_title as taskTitle, "
			+ "task_description as taskDescription, "
			+ "created_at as createdAt, "
			+ "updated_at as updatedAt, "
			+ "task_status as taskStatus "
			+ "FROM tasks")
	public List<TaskVO> findAll();

	//테스크 삭제 ->taskId를 기준으로 데이터베이스에서 작업을 제거
	 @Delete("DELETE FROM tasks WHERE task_id = #{taskId}")
	 public int deleteTask(@Param("taskId") int taskId);
	
	 
	//테스크 수정 ->기존 작업을 업데이트합니다. 
	//업데이트된 세부 정보를 가진 TaskVO 객체를 매개변수로 사용하고, 
	//tasks 테이블에서 해당 레코드를 수정
	 @Update("UPDATE tasks SET user_id = #{userId}, project_id = #{projectId}, " +
		        "task_title = #{taskTitle}, task_description = #{taskDescription}, " +
		        "task_status = #{taskStatus} " +
		        "WHERE task_id = #{taskId}")
		public int updateTask(TaskVO taskVo);

	 //0725 서브쿼리위해 쿼리수정
	 //task 상세 view 내용 조회 -> taskId를 기준으로 해당 task의 상세 뷰를 가져옵니다.
	@Select("SELECT task_id as taskId, "
			+ "user_id as userId, "
			+ "(select user_name from users where user_id = a.user_id limit 1) as userName, " //0725
			+ "(select user_position from users where user_id = a.user_id limit 1) as userPosition, " //0725
			+ "project_id as projectId, "
			+ "task_title as taskTitle, "
			+ "task_description as taskDescription, "
			+ "task_status as taskStatus, "
			+ "created_at as createdAt, "
			+ "updated_at as updatedAt "
			+ "FROM tasks a "
			+ "WHERE task_id = #{taskId}")
	public TaskVO findById(@Param("taskId") int taskId);

	
	//0725 
    //projectId로 task목록 조회 => project/detail.jsp로
    @Select("SELECT task_id as taskId, " +
			"(select user_name from users where user_id = a.user_id limit 1) as userName, " + //0725
			"(select user_position from users where user_id = a.user_id limit 1) as userPosition, " + //0725
            "project_id as projectId, " +
            "task_title as taskTitle, " +
            "task_description as taskDescription, " +
            "created_at as createdAt, " +
            "updated_at as updatedAt, " +
            "task_status as taskStatus " +
            "FROM tasks a " + 
            "WHERE project_id = #{projectId}")
    public List<TaskVO> getTasksByProjectId(@Param("projectId") Integer projectId, RowBounds rowBounds);
    
    //0725
    //project 목록 조회
    @Select("SELECT count(1)" + 
            "FROM tasks " +
    		"WHERE project_id = #{projectId}")
	public int getTotalTasksCountByProjectId(int projectId);
    // taskTitle로 조회해서 리스트를 불러오는 메서드, 소문자변환하고 부분일치검색을 수행한다
    // 검색된 결과의 총 개수를 반환하는 메서드
    
    
    
    //0725
    //taskTitle로 테스크 조회 => search.jsp로 
    @Select("SELECT task_id as taskId, " +
    		"(select user_name from users where user_id = a.user_id limit 1) as userName, " +//0725
			"(select user_position from users where user_id = a.user_id limit 1) as userPosition, " +//0725
            "project_id as projectId, " +
            "task_title as taskTitle, " +
            "task_description as taskDescription, " +
            "created_at as createdAt, " +
            "updated_at as updatedAt, " +
            "task_status as taskStatus " +
            "FROM tasks a " +
    		"WHERE LOWER(task_title) LIKE CONCAT('%', LOWER(#{taskVO.taskTitle}), '%')")
    // taskTitle로 조회해서 리스트 불러오는 SearcByTitle()
	public List<TaskVO> searchByTitle(@Param("taskVO") TaskVO taskVO, RowBounds rowBounds);
    //LOWER() 함수를 사용하여 테이블의 task_title 컬럼과 입력받은 taskTitle 파라미터를 모두 소문자로 변환합니다.   
    // 이렇게 하면 대소문자를 구분하지 않고 검색가능
    //LIKE 연산자와 CONCAT() 함수를 사용하여 부분 일치 검색을 구현합니다. 
    //% 와일드카드를 검색어 앞뒤에 추가하여 검색어가 제목의 어느 부분에 있어도 매치되도록
    //AS 키워드를 사용하여 각 컬럼에 별칭을 부여
    //
    
    //0725
    //taskTitle로 테스크 조회
    @Select("SELECT count(1) " + 
            "FROM tasks " +
			"WHERE LOWER(task_title) LIKE CONCAT('%', LOWER(#{taskVO.taskTitle}), '%')")
	public int getTotalTasksCount(@Param("taskVO") TaskVO taskVO);
    
}
