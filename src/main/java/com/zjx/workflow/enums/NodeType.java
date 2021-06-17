package com.zjx.workflow.enums;

/**
 * 节点类型
 *
 * @author cgn
 * @date 2021/06/15
 */
public enum NodeType {
    /**
     * 开始
     */
    START(0, "开始节点"),
    /**
     * 处理
     */
    BUSINESS(1, "业务节点"),
    /**
     * 分支
     */
    BRANCH(2, "分支节点"),
    /**
     * 结束
     */
    END(3, "结束节点");


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

    NodeType(Integer index, String description) {
        this.index = index;
        this.description = description;
    }
}
