package com.tongji.xiaohashu.user.biz.controller

import com.tongji.framework.biz.operationlog.aspect.ApiOperationLog
import com.tongji.xiaohashu.user.biz.model.vo.UpdateUserInfoReqVO
import com.tongji.xiaohashu.user.biz.service.UserService
import com.tongji.xiaohashu.user.dto.req.*
import lombok.extern.slf4j.Slf4j
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @time 2025/5/2 20:45
 * @author tongji
 * @description
 */
@RestController
@RequestMapping("/user")
@Slf4j
open class UserController(open val userService: UserService) {

    @PostMapping(value = ["/update"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ApiOperationLog(description = "更新用户信息")
    fun updateUserInfo(@Validated updateUserInfoReqVO: UpdateUserInfoReqVO?)
        = userService.updateUserInfo(updateUserInfoReqVO)!!

    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    fun register(@Validated @RequestBody registerUserReqDTO: RegisterUserReqDTO?)
        = userService.register(registerUserReqDTO)!!

    @PostMapping("/findByPhone")
    @ApiOperationLog(description = "手机号查询用户信息")
    fun findByPhone(@Validated @RequestBody findUserByPhoneReqDTO: FindUserByPhoneReqDTO?)
        = userService.findByPhone(findUserByPhoneReqDTO)!!

    @PostMapping("/password/update")
    @ApiOperationLog(description = "密码更新")
    fun updatePassword(@Validated @RequestBody updateUserPasswordReqDTO: UpdateUserPasswordReqDTO?)
        = userService.updatePassword(updateUserPasswordReqDTO)!!

    @PostMapping("/findById")
    @ApiOperationLog(description = "查询用户信息")
    fun findById(@Validated @RequestBody findUserByIdReqDTO: FindUserByIdReqDTO?)
        = userService.findById(findUserByIdReqDTO)!!

    @PostMapping("/findByIds")
    @ApiOperationLog(description = "批量查询用户信息")
    fun findByIds(@Validated @RequestBody findUsersByIdsReqDTO: FindUserByIdsReqDTO?)
        = userService.findByIds(findUsersByIdsReqDTO)!!
}