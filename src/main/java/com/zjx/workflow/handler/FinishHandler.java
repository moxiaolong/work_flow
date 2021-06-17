package com.zjx.workflow.handler;

import com.zjx.workflow.entity.Instance;
import org.springframework.stereotype.Component;


/**
 * 完成处理程序
 *
 * @author cgn
 * @date 2021/06/15
 */
@Component
public class FinishHandler extends Handler {
    @Override
    public void onHandle(Instance instance) {
         complete(instance);
    }
}
