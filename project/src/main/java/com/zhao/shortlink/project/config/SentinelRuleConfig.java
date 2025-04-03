

package com.zhao.shortlink.project.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化限流配置
 * 小赵
 */
@Component
public class SentinelRuleConfig implements InitializingBean {

    /**
     * 定义流控规则
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule createOrderRule = new FlowRule();
        createOrderRule.setResource("create_short-link");
        // 使用QPS限制
        createOrderRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 每秒最多一次
        createOrderRule.setCount(1);
        rules.add(createOrderRule);
        FlowRuleManager.loadRules(rules);
    }
}
