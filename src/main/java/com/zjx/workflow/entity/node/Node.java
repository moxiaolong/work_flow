package com.zjx.workflow.entity.node;

import com.zjx.workflow.config.HandlerRepository;
import com.zjx.workflow.entity.BaseEntity;
import com.zjx.workflow.entity.Conditions;
import com.zjx.workflow.enums.NodeType;
import com.zjx.workflow.handler.BranchHandler;
import com.zjx.workflow.handler.FinishHandler;
import com.zjx.workflow.handler.Handler;
import com.zjx.workflow.handler.DefaultHandler;
import com.zjx.workflow.handler.business.BusinessHandler;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;

import javax.persistence.*;
import java.util.List;

/**
 * 节点
 *
 * @author cgn
 * @date 2021/06/15
 */
@Data
@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class Node extends BaseEntity {


    @Column
    public String name;
    @Column(columnDefinition = "bit default b'0' not null")
    public Boolean autoProceed;

    /**
     * 条件 字段 to 字段正则表达式
     */
    @OneToMany
    private List<Conditions> conditions;

    /**
     * @see NodeType
     */
    @Column
    private NodeType type;

    /**
     * 条件成立节点
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Node tNode;
    /**
     * 条件不成立节点
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Node fNode;

    /**
     * 业务处理操作人组ID
     */
    @Column
    private Integer businessGroupId;
    /**
     * 下一节点
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Node nextNode;
    /**
     * 业务处理器类名
     */
    @Column
    private String businessHandlerClassName;

    /**
     * 获取对应处理器
     *
     * @return 处理器
     */
    public Handler getHandler() {
        return HandlerRepository.getHandler(this);
    }


}
