package com.techyourchance.testdoublesfundamentals.exercise4;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync.EndpointResult;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

public class FetchUserProfileUseCaseSyncTest {

    FetchUserProfileUseCaseSync SUT;
    UserProfileHttpEndpointSyncTD userProfileHttpEndpointSyncTD;
    UsersCacheTD usersCacheTD;

    String userId = "1234";
    String fullName = "FullName";
    String imageUrl = "*/imageurl/*";

    @Before
    public void setUp() throws Exception {
        userProfileHttpEndpointSyncTD = new UserProfileHttpEndpointSyncTD();
        usersCacheTD = new UsersCacheTD();
        SUT = new FetchUserProfileUseCaseSync(userProfileHttpEndpointSyncTD, usersCacheTD);
    }


    @Test
    public void fetchSync_incorrectUserId_useCaseResultFailure() {
        userProfileHttpEndpointSyncTD.endpointResult = new EndpointResult(
                UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS,
                userId,
                fullName,
                imageUrl
        );

        usersCacheTD.cacheUser(new User(
                userId,
                fullName,
                imageUrl
        ));
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(userId);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }
 @Test
    public void fetchSync_success_useCaseResultSuccess() {
        userProfileHttpEndpointSyncTD.endpointResult = new EndpointResult(
                UserProfileHttpEndpointSync.EndpointResultStatus.SUCCESS,
                userId,
                fullName,
                imageUrl
        );

        usersCacheTD.cacheUser(new User(
                userId,
                fullName,
                imageUrl
        ));
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(userId);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.SUCCESS));
    }

    @Test
    public void fetchSync_generalError_useCaseResultFailure() {

        userProfileHttpEndpointSyncTD.endpointResult = new EndpointResult(
                UserProfileHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR,
                userId,
                fullName,
                imageUrl
        );

        usersCacheTD.cacheUser(new User(
                userId,
                fullName,
                imageUrl
        ));
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(userId);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchSync_authError_useCaseResultFailure() {

        userProfileHttpEndpointSyncTD.endpointResult = new EndpointResult(
                UserProfileHttpEndpointSync.EndpointResultStatus.AUTH_ERROR,
                userId,
                fullName,
                imageUrl
        );

        usersCacheTD.cacheUser(new User(
                userId,
                fullName,
                imageUrl
        ));
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(userId);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.FAILURE));
    }

    @Test
    public void fetchSync_serverError_useCaseResultNetworkError() {

        userProfileHttpEndpointSyncTD.endpointResult = new EndpointResult(
                UserProfileHttpEndpointSync.EndpointResultStatus.SERVER_ERROR,
                userId,
                fullName,
                imageUrl
        );

        usersCacheTD.cacheUser(new User(
                userId,
                fullName,
                imageUrl
        ));
        FetchUserProfileUseCaseSync.UseCaseResult result = SUT.fetchUserProfileSync(userId);
        assertThat(result, is(FetchUserProfileUseCaseSync.UseCaseResult.NETWORK_ERROR));
    }


    private static class UserProfileHttpEndpointSyncTD implements UserProfileHttpEndpointSync {

        public EndpointResult endpointResult;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            return endpointResult;
        }

    }


    private static class UsersCacheTD implements UsersCache {

        private User user;

        @Override
        public void cacheUser(User user) {
            this.user = user;
        }

        @Nullable
        @Override
        public User getUser(String userId) {
            return user;
        }

    }


}