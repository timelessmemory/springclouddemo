import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring.xml"
})
@Slf4j
public class APITest {

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;

    /**
     * 部署流程定义（从classpath）
     */
    @Test
    public void deploymentProcessDefinition_classpath(){
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("审批流程部署")//添加部署的名称
                .addClasspathResource("bpmn/MyProcess.bpmn")//从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource("bpmn/MyProcess.png")//从classpath的资源中加载，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());
        System.out.println("部署名称："+deployment.getName());
    }

    /**
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义的查询
                /**指定查询条件,where条件*/
//                      .deploymentId(deploymentId)//使用部署对象ID查询
//                      .processDefinitionId(processDefinitionId)//使用流程定义ID查询
//                      .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//                      .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /**排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//                      .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                /**返回的结果集*/
                .list();//返回一个集合列表，封装流程定义
//                      .singleResult();//返回惟一结果集
//                      .count();//返回结果集数量
//                      .listPage(firstResult, maxResults);//分页查询
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:"+pd.getName());//对应hello.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+pd.getKey());//对应hello.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:"+pd.getResourceName());
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                System.out.println("部署对象ID："+pd.getDeploymentId());
                System.out.println("*********************************************");
            }
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefinition(){
        //使用部署ID，完成删除，指定部署对象id为1删除
        String deploymentId = "22501";
        /**
         * 不带级联的删除
         *    只能删除没有启动的流程，如果流程启动，就会抛出异常
         */
//      processEngine.getRepositoryService()//
//                      .deleteDeployment(deploymentId);

        /**
         * 级联删除
         *    不管流程是否启动，都能可以删除
         */
        processEngine.getRepositoryService()//
                .deleteDeployment(deploymentId, true);
        System.out.println("删除成功！");
    }

    /**
     * 查看流程图
     *
     * @throws IOException
     */
    @Test
    public void viewPic() throws IOException {
        /**将生成图片放到文件夹下*/
        String deploymentId = "1";
        //获取图片资源名称
        List<String> list = processEngine.getRepositoryService()//
                .getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if (list != null && list.size() > 0) {
            for (String name : list) {
                System.out.println(name);
                if (name.indexOf(".png") >= 0) {
                    resourceName = name;
                }
            }
        }

        //获取图片的输入流
        InputStream in = processEngine.getRepositoryService()//
                .getResourceAsStream(deploymentId, resourceName);

        //将图片生成到F盘的目录下
        File file = new File("F:/" + resourceName);

        //将输入流的图片写到磁盘
        FileUtils.copyInputStreamToFile(in, file);
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance(){
        //1、流程定义的key，通过这个key来启动流程实例
        String processDefinitionKey = "myProcess";
        //2、与正在执行的流程实例和执行对象相关的Service
        // startProcessInstanceByKey方法还可以设置其他的参数，比如流程变量。
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        log.info("流程实例ID:"+pi.getId());//流程实例ID
        log.info("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findPersonalTask(){
        String assignee = "xiaowang";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
//                      .taskCandidateUser(candidateUser)//组任务的办理人查询
//                      .processDefinitionId(processDefinitionId)//使用流程定义ID查询
//                      .processInstanceId(processInstanceId)//使用流程实例ID查询
//                      .executionId(executionId)//使用执行对象ID查询
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
//                      .singleResult()//返回惟一结果集
//                      .count()//返回结果集的数量
//                      .listPage(firstResult, maxResults);//分页查询
                .list();//返回列表
        if(list!=null && list.size()>0){
            for(Task task:list){
                log.info("任务ID:"+task.getId());
                log.info("任务名称:"+task.getName());
                log.info("任务的创建时间:"+task.getCreateTime());
                log.info("任务的办理人:"+task.getAssignee());
                log.info("流程实例ID："+task.getProcessInstanceId());
                log.info("执行对象ID:"+task.getExecutionId());
                log.info("流程定义ID:"+task.getProcessDefinitionId());
                log.info("********************************************");
            }
        }
    }

    @Test
    public void completePersonalTask() {
        //任务ID，上一步查询得到的。
        String taskId = "30004";
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId);
        log.info("完成任务：任务ID：" + taskId);
    }

    /**
     * 查询流程状态（判断流程走到哪一个节点）
     */
    @Test
    public void isProcessActive() {
        String processInstanceId = "30001";
        ProcessInstance pi = processEngine.getRuntimeService()//表示正在执行的流程实例和执行对象
                .createProcessInstanceQuery()//创建流程实例查询
                .processInstanceId(processInstanceId)//使用流程实例ID查询
                .singleResult();
        if (pi == null) {
            log.info("流程已经结束");
        } else {
            log.info("流程没有结束");
            //获取任务状态
            log.info("节点id：" + pi.getActivityId());
        }
    }


    //history

    /**
     * 历史活动查询接口
     */
    @Test
    public void findHistoryActivity() {
        String processInstanceId = "30001";
        List<HistoricActivityInstance> hais = processEngine.getHistoryService()//
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricActivityInstance hai : hais) {
            log.info("活动id：" + hai.getActivityId()
                    + "   审批人：" + hai.getAssignee()
                    + "   任务id：" + hai.getTaskId());
            log.info("************************************");
        }
    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void findHistoryProcessInstance() {
        String processInstanceId = "30001";
        HistoricProcessInstance hpi = processEngine.getHistoryService()// 与历史数据（历史表）相关的Service
                .createHistoricProcessInstanceQuery()// 创建历史流程实例查询
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .orderByProcessInstanceStartTime().asc().singleResult();
        log.info(hpi.getId() + "    " + hpi.getProcessDefinitionId() + "    " + hpi.getStartTime() + "    "
                + hpi.getEndTime() + "     " + hpi.getDurationInMillis());
    }

    /**
     * 查询历史任务
     */
    @Test
    public void findHistoryTask() {
        String processInstanceId = "30001";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()// 与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricTaskInstanceStartTime().asc().list();
        if (list != null && list.size() > 0) {
            for (HistoricTaskInstance hti : list) {
                log.info("\n 任务Id：" + hti.getId() + "    任务名称：" + hti.getName() + "    流程实例Id：" + hti.getProcessInstanceId() + "\n 开始时间："
                        + hti.getStartTime() + "   结束时间：" + hti.getEndTime() + "   持续时间：" + hti.getDurationInMillis());
            }
        }
    }

    /**
     * 查询历史流程变量
     */
    @Test
    public void findHistoryProcessVariables() {
        String processInstanceId = "30001";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()//
                .createHistoricVariableInstanceQuery()// 创建一个历史的流程变量查询对象
                .processInstanceId(processInstanceId)//
                .list();
        if (list != null && list.size() > 0) {
            for (HistoricVariableInstance hvi : list) {
                log.info("\n" + hvi.getId() + "   " + hvi.getProcessInstanceId() + "\n" + hvi.getVariableName()
                        + "   " + hvi.getVariableTypeName() + "    " + hvi.getValue());
            }
        }
    }

    /**
     * 通过执行sql来查询历史数据，由于activiti底层就是数据库表。
     */
    @Test
    public void findHistoryByNative() {
        HistoricProcessInstance hpi = processEngine.getHistoryService()
                .createNativeHistoricProcessInstanceQuery()
                .sql("查询底层数据库表的sql语句")
                .singleResult();
        log.info("\n" + hpi.getId() + "    " + hpi.getProcessDefinitionId() + "    " + hpi.getStartTime()
                + "\n" + hpi.getEndTime() + "     " + hpi.getDurationInMillis());
    }
}