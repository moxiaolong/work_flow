package com.zjx.workflow.handler;

import com.zjx.workflow.entity.node.Node;

import com.zjx.workflow.entity.HistoryRecord;
import com.zjx.workflow.entity.Instance;
import com.zjx.workflow.enums.InstanceStatus;
import com.zjx.workflow.repository.HistoryRecordRepository;
import com.zjx.workflow.repository.InstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * 处理程序
 *
 * @author cgn
 * @date 2021/06/15
 */
@Slf4j
public abstract class Handler {

    @Autowired
    HistoryRecordRepository historyRecordRepository;

    @Autowired
    InstanceRepository instanceRepository;


    /**
     * 处理
     *
     * @param instance 流实例
     */
    final public void handle(Instance instance) {
        log.info("处理任务,实例ID#{},处理器#{}", instance.getId(), this.getClass().getSimpleName());
        onHandle(instance);
    }

    /**
     * 处理
     *
     * @param instance
     */
    public abstract void onHandle(Instance instance);

    /**
     * 完成
     *
     * @param instance
     */
    public void complete(Instance instance) {
        Node nextNode = instance.getNode().getNextNode();
        complete(instance, nextNode);
    }

    /**
     * 完成
     *
     * @param instance
     */
    public void complete(Instance instance, Node nextNode) {
        log.info("处理结束,实例ID#{},节点ID#{},处理器#{}", instance.getNode().getId(), instance.getId(), this.getClass().getSimpleName());

        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setCurrentNode(instance.getNode());
        /*
          如果下节点为空 标记状态为完成
         */
        if (nextNode == null) {
            instance.setStatus(InstanceStatus.FINISH);
        }
        instance.setNode(nextNode);

        instanceRepository.saveAndFlush(instance);

        /*
          保存历史纪录
         */
        historyRecord.setFlowChain(instance.getFlowChain());
        historyRecord.setNode(instance.getNode());
        historyRecord.setSourceJson(instance.getSourceJson());
        historyRecordRepository.save(historyRecord);


        /*
          如果下节点自动，继续向下执行
         */
        if (instance.getNode() != null && instance.getNode().getAutoProceed()) {
            instance.getNode().getHandler().handle(instance);
        }
    }


}
