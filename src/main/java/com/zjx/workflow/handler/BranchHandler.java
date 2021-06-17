package com.zjx.workflow.handler;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjx.workflow.entity.Conditions;
import com.zjx.workflow.entity.Instance;
import com.zjx.workflow.entity.node.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 分支处理程序
 * 通过关联条件做校验 下一节点指向 T_node 或 F_node
 *
 * @author cgn
 * @date 2021/06/15
 */
@Component
public class BranchHandler extends Handler {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onHandle(Instance instance) {
        Node currentNode = instance.getNode();
        String sourceJson = instance.getSourceJson();
        try {
            Map<String, String> sourceMap = objectMapper.readValue(sourceJson, new TypeReference<Map<String, String>>() {
            });

            List<Conditions> conditions = currentNode.getConditions();
            for (Conditions condition : conditions) {
                if (!condition.check(sourceMap)) {
                    //校验未通过
                    complete(instance, instance.getNode().getFNode());
                    return;
                }
            }
            //校验通过
            complete(instance, instance.getNode().getTNode());

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
