import com.zjx.workflow.WorkFlowApplication;
import com.zjx.workflow.entity.Conditions;
import com.zjx.workflow.entity.node.FlowChain;
import com.zjx.workflow.entity.node.Node;
import com.zjx.workflow.enums.NodeType;
import com.zjx.workflow.repository.ConditionsRepository;
import com.zjx.workflow.repository.FlowChainRepository;
import com.zjx.workflow.repository.NodeRepository;
import com.zjx.workflow.service.FlowService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;

/**
 * 测试流程
 * 定义4个节点  A指向B A为自动节点 自动通过 B指向C  B需要手动激活  B为条件节点 成立指向D 不成立指向C  C为自动节点 指向D  D为结束节点
 */
@SpringBootTest(classes = WorkFlowApplication.class)
@RunWith(SpringRunner.class)
public class TestApplication {

    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    FlowChainRepository flowChainRepository;

    @Autowired
    ConditionsRepository conditionsRepository;

    @Autowired
    FlowService flowService;

    @Test
    @Transactional
    public void test() {


        Node nodeA = new Node();
        nodeA.setAutoProceed(true);
        nodeA.setType(NodeType.START);
        nodeRepository.save(nodeA);
        nodeA = nodeRepository.findById(nodeA.getId()).get();

        Node nodeB = new Node();
        Conditions conditionsA = new Conditions();
        Conditions conditionsB = new Conditions();
        conditionsA.setFieldName("fieldA");
        conditionsA.setReg("^[0-9]*$");
        conditionsB.setFieldName("fieldB");
        conditionsB.setReg("^[0-9]*$");
        ArrayList<Conditions> conditionsArrayList = new ArrayList<>();
        conditionsArrayList.add(conditionsA);
        conditionsArrayList.add(conditionsB);
        conditionsRepository.saveAll(conditionsArrayList);

        nodeB.setConditions(conditionsArrayList);

        nodeB.setAutoProceed(false);
        nodeB.setType(NodeType.BRANCH);
        nodeRepository.save(nodeB);
        nodeB = nodeRepository.findById(nodeB.getId()).get();

        Node nodeC = new Node();
        nodeC.setAutoProceed(true);
        nodeRepository.save(nodeC);
        nodeC = nodeRepository.findById(nodeC.getId()).get();

        Node nodeD = new Node();
        nodeD.setType(NodeType.END);
        nodeD.setAutoProceed(true);
        nodeRepository.save(nodeD);
        nodeD = nodeRepository.findById(nodeD.getId()).get();

        nodeA.setNextNode(nodeB);
        nodeRepository.save(nodeA);
        nodeB.setTNode(nodeD);
        nodeB.setFNode(nodeC);
        nodeRepository.save(nodeB);
        nodeC.setNextNode(nodeD);
        nodeRepository.save(nodeC);

        FlowChain flowChain = new FlowChain();
        flowChain.setName("测试链");
        flowChain.setStartNode(nodeA);
        flowChainRepository.save(flowChain);

        Integer instanceId = flowService.startTask(flowChain.getId(), null);


        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flowService.activateTask(instanceId, null);

        try {
            Thread.sleep(52000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
