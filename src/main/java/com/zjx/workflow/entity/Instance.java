package com.zjx.workflow.entity;

import com.zjx.workflow.entity.node.FlowChain;
import com.zjx.workflow.entity.node.Node;
import com.zjx.workflow.enums.InstanceStatus;
import com.zjx.workflow.handler.Handler;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 实例
 *
 * @author cgn
 * @date 2021/06/15
 */
@Data
@Table
@Entity
@DynamicInsert
@DynamicUpdate
public class Instance extends BaseEntity {
    /**
     * 流
     */
    @ManyToOne
    private FlowChain flowChain;

    /**
     * 指向的node
     */
    @OneToOne
    private Node node;

    /**
     * 携带资源
     */
    @Column
    private String sourceJson;

    /**
     * 业务操作人组
     */
    private Integer businessUserGroup;

    /**
     * 流状态
     */
    @Column
    private InstanceStatus status;

    /**
     * 激活当前实例
     */
    public void activate() {
        Handler handler = this.getNode().getHandler();
        handler.handle(this);
    }
}
