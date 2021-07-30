package com.techyourchance.mockitofundamentals.exercise5;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.techyourchance.mockitofundamentals.exercise5.eventbus.EventBusPoster;
import com.techyourchance.mockitofundamentals.exercise5.networking.NetworkErrorException;
import com.techyourchance.mockitofundamentals.exercise5.networking.UpdateUsernameHttpEndpointSync;
import com.techyourchance.mockitofundamentals.exercise5.users.User;
import com.techyourchance.mockitofundamentals.exercise5.users.UsersCache;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUsernameUseCaseSyncTest {

    static final String USER_ID = "user id";
    static final String USER_NAME = "username";

    UpdateUsernameUseCaseSync SUT;
    @Mock UpdateUsernameHttpEndpointSync updateUsernameHttpEndpointSyncMock;
    @Mock UsersCache usersCacheMock;
    @Mock EventBusPoster eventBusPosterMock;

    @Before
    public void setUp() throws Exception {
       SUT = new UpdateUsernameUseCaseSync(
                updateUsernameHttpEndpointSyncMock,
                usersCacheMock,
                eventBusPosterMock
        );
        success();
    }
    
    @Test
    public void updateSync_success_idAndUserNamePassedToEndpoint() throws Exception {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.updateUsernameSync(USER_ID, USER_NAME);
        verify(updateUsernameHttpEndpointSyncMock, times(1))
                .updateUsername(ac.capture(), ac.capture());
        List<String> captures = ac.getAllValues();
        assertEquals(captures.get(0), USER_ID);
        assertEquals(captures.get(1), USER_NAME);
    }

    @Test
    public void updateSync_success_userCached() {
        ArgumentCaptor<User> ac = ArgumentCaptor.forClass(User.class);
        SUT.updateUsernameSync(USER_ID, USER_NAME);
        verify(usersCacheMock).cacheUser(ac.capture());
        User captured = ac.getValue();
        assertEquals(USER_ID, captured.getUserId());
        assertEquals(USER_NAME, captured.getUsername());
    }

    @Test
    public void updateSync_generalError_userNotCached() throws NetworkErrorException {
        generalError();
        SUT.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(usersCacheMock);
    }

    @Test
    public void updateSync_authError_userNotCached() throws Exception {
        authError();
        SUT.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(usersCacheMock);
    }

    @Test
    public void updateSync_serverError_userNotCached() throws Exception {
        serverError();
        SUT.updateUsernameSync(USER_ID, USER_NAME);
        verifyNoMoreInteractions(usersCacheMock);
    }

    @Test
    public void updateSync_networkError_networkErrorReturned() throws Exception {
        networkError();
        UpdateUsernameUseCaseSync.UseCaseResult result = SUT.updateUsernameSync(USER_ID, USER_NAME);
        assertEquals(UpdateUsernameUseCaseSync.UseCaseResult.NETWORK_ERROR, result);
    }



    private void networkError() throws NetworkErrorException {
        doThrow(NetworkErrorException.class).when(updateUsernameHttpEndpointSyncMock).updateUsername(anyString(), anyString());
    }

    private void generalError() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(any(String.class), any(String.class)))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR, USER_ID, USER_NAME));
    }

    private void authError() throws Exception {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(anyString(), anyString()))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.AUTH_ERROR, "", ""));
    }

    private void serverError() throws Exception {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(anyString(), anyString()))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SERVER_ERROR, "", ""));
    }
    private void success() throws NetworkErrorException {
        when(updateUsernameHttpEndpointSyncMock.updateUsername(any(String.class), any(String.class)))
                .thenReturn(new UpdateUsernameHttpEndpointSync.EndpointResult(UpdateUsernameHttpEndpointSync.EndpointResultStatus.SUCCESS, USER_ID, USER_NAME));
    }
}