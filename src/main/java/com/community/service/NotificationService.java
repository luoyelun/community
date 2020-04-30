package com.community.service;

import com.community.Exception.CustomizeErrorCode;
import com.community.Exception.CustomizeException;
import com.community.dto.NotificationDTO;
import com.community.enums.NotificationStatusEnum;
import com.community.enums.NotificationTypeEnum;
import com.community.mapper.NotificationMapper;
import com.community.mapper.UserMapper;
import com.community.model.Notification;
import com.community.model.NotificationExample;
import com.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author luoyelun
 * @date 2020/4/30 17:55
 */
@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public PageInfo<NotificationDTO> list(Integer pageNum, Long accountId) {
        PageHelper.startPage(pageNum, 10);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiveEqualTo(accountId);
        notificationExample.setOrderByClause("GMT_CREATE desc");
        PageInfo<Notification> notifications = new PageInfo<>(notificationMapper.selectByExample(notificationExample));
        PageInfo<NotificationDTO> pageInfo = new PageInfo<>(new ArrayList<>());

        for (Notification notification : notifications.getList()) {
            NotificationDTO dto = new NotificationDTO();
            BeanUtils.copyProperties(notification, dto);
            dto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            pageInfo.getList().add(dto);
        }
        return pageInfo;
    }

    public Long unreadCount(Long accountId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiveEqualTo(accountId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }

    public NotificationDTO read(int id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceive(), user.getAccountId())) {
            throw new CustomizeException(CustomizeErrorCode.SYS_ERROR);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO dto = new NotificationDTO();
        BeanUtils.copyProperties(notification, dto);
        dto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return dto;
    }
}
