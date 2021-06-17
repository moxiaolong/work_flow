package com.zjx.workflow.handler;

import com.zjx.workflow.entity.Instance;
import org.springframework.stereotype.Component;


/**
 * 直接通过
 *
 * @author cgn
 * @date 2021/06/15
 */
@Component
public class DefaultHandler extends Handler {
    @Override
    public void onHandle(Instance instance) {
         complete(instance);
    }
}
