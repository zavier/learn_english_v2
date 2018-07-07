package com.zavier.lenglish.config;

import com.zavier.lenglish.dao.PermissionsMapper;
import com.zavier.lenglish.dao.RolesMapper;
import com.zavier.lenglish.dao.UsersMapper;
import com.zavier.lenglish.pojo.Permissions;
import com.zavier.lenglish.pojo.Roles;
import com.zavier.lenglish.pojo.Users;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ShiroRealm extends JdbcRealm {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;

        String username = uToken.getUsername();

        if (StringUtils.isBlank(username)) {
            throw new UnknownAccountException("用户名不存在");
        }
        Users users = usersMapper.selectByUserName(username);
        if (users == null) {
            throw new UnknownAccountException("用户名不存在");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, users.getPassword(), getName());
        // 由于注册的时候使用的盐为「用户名+盐」，且数据库中只存储了盐的后半部分（不包含用户名），所以这里需要自己拼接一下
        info.setCredentialsSalt(ByteSource.Util.bytes(users.getUsername() + users.getPasswordSalt()));
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Roles> rolesList = rolesMapper.selectRolesByUserName(username);
        if (CollectionUtils.isEmpty(rolesList)) {
            return simpleAuthorizationInfo;
        }
        List<String> roleNames = rolesList.stream().map(r -> r.getRoleName()).collect(Collectors.toList());
        simpleAuthorizationInfo.addRoles(roleNames);
        List<Permissions> permissions = permissionsMapper.selectByRolesName(roleNames);
        if (CollectionUtils.isEmpty(permissions)) {
            return simpleAuthorizationInfo;
        }
        List<String> permissionNames = permissions.stream().map(p -> p.getPermission()).collect(Collectors.toList());
        simpleAuthorizationInfo.addStringPermissions(permissionNames);
        return simpleAuthorizationInfo;
    }
}
