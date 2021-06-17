package com.zjx.workflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjx.workflow.entity.Instance;
import com.zjx.workflow.entity.node.FlowChain;
import com.zjx.workflow.enums.InstanceStatus;
import com.zjx.workflow.repository.FlowChainRepository;
import com.zjx.workflow.repository.InstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * 开始流
 *
 * @author cgn
 * @date 2021/06/15
 */
@Service
@Slf4j
public class FlowService {
    @Autowired
    FlowChainRepository flowChainRepository;

    @Autowired
    InstanceRepository instanceRepository;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 开启一个任务
     *
     * @param flowId
     * @param sourceJson
     * @return 任务id
     */
    public Integer startTask(Integer flowId, String sourceJson) {
        FlowChain flowChain = flowChainRepository.findById(flowId).get();
        Instance instance = new Instance();
        instance.setFlowChain(flowChain);
        instance.setSourceJson(sourceJson);
        instance.setNode(flowChain.getStartNode());
        instance.setStatus(InstanceStatus.PROCESSING);
        instanceRepository.saveAndFlush(instance);
        if (instance.getNode().getAutoProceed()) {
            activateTask(instance, null);
        }
        log.info("开启#{} 流,实例ID#{}", flowChain.getName(), instance.getId());
        return instance.getId();

    }

    /**
     * 激活任务 根据ID
     *
     * @param instanceId
     * @param sourceJson
     */
    public void activateTask(Integer instanceId, String sourceJson) {
        Optional<Instance> byId = instanceRepository.findById(instanceId);
        if (!byId.isPresent()) {
            throw new RuntimeException("查询不到对应的任务实例");
        }
        activateTask(byId.get(), sourceJson);
    }


    /**
     * 激活任务
     *
     * @param instance
     * @param sourceJson
     */
    @Async
    public void activateTask(Instance instance, String sourceJson) {
        InstanceStatus status = instance.getStatus();

        if (instance.getStatus().equals(InstanceStatus.FINISH)) {
            throw new RuntimeException("已结束的任务不可激活");
        }
        String oldSource = instance.getSourceJson();
        if (sourceJson != null) {
            try {
                Map<String, String> oldSourceMap = objectMapper.readValue(oldSource, new TypeReference<Map<String, String>>() {
                });
                Map<String, String> newSourceMap = objectMapper.readValue(sourceJson, new TypeReference<Map<String, String>>() {
                });
                oldSourceMap.putAll(newSourceMap);
                oldSource = objectMapper.writeValueAsString(oldSourceMap);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            instance.setSourceJson(oldSource);
        }

        log.info("激活流,实例ID#{},当前参数#{}", instance.getId(), oldSource);
        instance.activate();
    }

    /**
     * 查询任务
     *
     * @param taskId 任务ID
     * @return
     */
    public Optional<Instance> queryTask(Integer taskId) {
        return instanceRepository.findById(taskId);
    }

}
