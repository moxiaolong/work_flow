package com.zjx.workflow.entity;

import com.zjx.workflow.entity.node.Node;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 历史记录
 *
 * @author cgn
 * @date 2021/06/15
 */
@Data
@Table
@Entity
@DynamicInsert
@DynamicUpdate
public class HistoryRecord extends Instance {

    /**
     * 当前节点
     */
    @OneToOne
    private Node currentNode;
}
