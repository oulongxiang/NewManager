package com.tul.manage.security.entity;

import com.tul.manage.warning.vo.response.RiskInfoVo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class RiskInfoDTO implements Serializable {
    private MultipartFile uploadFile;
    private RiskInfoVo riskInfoVo;
}


