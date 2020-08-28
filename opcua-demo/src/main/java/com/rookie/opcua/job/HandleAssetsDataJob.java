package com.rookie.opcua.job;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.opcua.dto.ProfitCenterDTO;
import com.rookie.opcua.entity.*;
import com.rookie.opcua.mapper.*;
import com.rookie.opcua.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
//@Component
public class HandleAssetsDataJob {


}
