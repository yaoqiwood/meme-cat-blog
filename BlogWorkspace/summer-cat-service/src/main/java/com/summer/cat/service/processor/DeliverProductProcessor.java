package com.summer.cat.service.processor;

import com.summer.cat.mapper.OrderMapper;
import com.summer.cat.entity.Order;
import com.summer.cat.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单发货处理器
 * @author summer
 * @since 2018-10-17
 */
@Component("deliverProductProcessor")
public class DeliverProductProcessor extends ActionProcessor{

	@Override
	public Order process(OrderModel orderDef, Order currentOrder) throws Exception {
		return this.deliver(orderDef,currentOrder);
	}

	@Autowired
    OrderMapper orderMapper;
	/**
	 * 订单发货
	 * @param currentOrder
	 * @return
	 */
	 private Order deliver(OrderModel orderModel, Order currentOrder) throws Exception {
		//更新订单信息
		currentOrder.setOrderStatus(orderModel.getOrderStatus());
		currentOrder.setCreateTime(System.currentTimeMillis());
		 orderMapper.updateById(currentOrder);
		return currentOrder;
	}
}
