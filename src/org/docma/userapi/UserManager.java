/*
 * UserManager.java
 *
 *  Copyright (C) 2013  Manfred Paula, http://www.docmenta.org
 *   
 *  This file is part of Docmenta. Docmenta is free software: you can 
 *  redistribute it and/or modify it under the terms of the GNU Lesser 
 *  General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Docmenta.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.docma.userapi;

/**
 *
 * @author MP
 */
public interface UserManager {

    String      createUser(String userName) throws Exception;
    void        deleteUser(String userId) throws Exception;

    String[]    getUserIds();
    String      getUserIdFromName(String userName);
    String      getUserNameFromId(String userId);
    void        setUserName(String userId, String newUserName) throws Exception;
    void        setPassword(String userId, String newPassword) throws Exception;
    boolean     verifyUserNamePassword(String userName, String password);

    String      getUserProperty(String userId, String propName);
    void        setUserProperty(String userId, String propName, String propValue) throws Exception;
    void        setUserProperties(String userId, String[] propNames, String[] propValues) throws Exception;

    String      createGroup(String groupName) throws Exception;
    void        deleteGroup(String groupId) throws Exception;

    String[]    getGroupIds();
    String      getGroupNameFromId(String groupId);
    String      getGroupIdFromName(String groupName);
    void        setGroupName(String groupId, String newGroupName) throws Exception;

    String      getGroupProperty(String groupId, String propName);
    void        setGroupProperty(String groupId, String propName, String propValue) throws Exception;
    void        setGroupProperties(String groupId, String[] propNames, String[] propValues) throws Exception;

    boolean     isUserInGroup(String userId, String groupId);
    String[]    getUsersInGroup(String groupId);
    String[]    getGroupsOfUser(String userId);
    void        setGroupsOfUser(String userId, String[] groupIds) throws Exception;
    boolean     addUserToGroup(String userId, String groupId) throws Exception;
    int         addUsersToGroup(String[] userIds, String groupId) throws Exception;
    boolean     removeUserFromGroup(String userId, String groupId) throws Exception;
    int         removeUsersFromGroup(String[] userIds, String groupId) throws Exception;

}
