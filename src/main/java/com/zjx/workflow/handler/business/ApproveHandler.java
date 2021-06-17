package com.zjx.workflow.handler.business;

import com.zjx.workflow.entity.Instance;
import org.springframework.stereotype.Component;

/**
 * 批准处理程序
 *
 * @author cgn
 * @date 2021/06/15
 */
@Component
public class ApproveHandler extends BusinessHandler {
    @Override
    public void onHandle(Instance instance) {
        complete(instance);

        //   complete(instance, null);

    }
}
