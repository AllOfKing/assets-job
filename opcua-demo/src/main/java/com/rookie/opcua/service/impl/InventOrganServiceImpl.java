package com.rookie.opcua.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.opcua.entity.InventOrgan;
import com.rookie.opcua.mapper.InventOrganMapper;
import com.rookie.opcua.service.InventOrganService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统部门(InventOrgan)表服务实现类
 *
 * @author jxj
 * @since 2020-09-09 10:49:07
 */
@Service("inventOrganService")
@AllArgsConstructor
public class InventOrganServiceImpl extends ServiceImpl<InventOrganMapper, InventOrgan> implements InventOrganService {

}