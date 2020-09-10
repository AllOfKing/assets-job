package com.rookie.opcua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.opcua.mapper.InventBachMapper;
import com.rookie.opcua.entity.InventBatch;
import com.rookie.opcua.service.InvenBachService;
import org.springframework.stereotype.Service;

/**
 * 批次表(InvenBach)表服务实现类
 *
 * @author jxj
 * @since 2020-09-10 19:00:42
 */
@Service
public class InvenBachServiceImpl extends ServiceImpl<InventBachMapper, InventBatch> implements InvenBachService {

}