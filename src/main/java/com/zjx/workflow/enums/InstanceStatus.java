package com.zjx.workflow.enums;

/**
 * 节点类型
 *
 * @author cgn
 * @date 2021/06/15
 */
public enum InstanceStatus {
    /**
     * 进行中
     */
    PROCESSING(0, "进行中"),
    /**
     * 已结束
     */
    FINISH(1, "已结束");


    /**
     * 标记
     */
    private final Integer index;
    /**
     * 描述
     */
    private final String description;

    public Integer getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    InstanceStatus(Integer index, String description) {
        this.index = index;
        this.description = description;
    }
}
