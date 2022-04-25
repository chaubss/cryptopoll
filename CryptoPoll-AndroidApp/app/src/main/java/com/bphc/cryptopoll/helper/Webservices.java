package com.bphc.cryptopoll.helper;

import static com.bphc.cryptopoll.app.Constants.CAST_VOTE;
import static com.bphc.cryptopoll.app.Constants.CREATE_POLL;
import static com.bphc.cryptopoll.app.Constants.GET_BLOCKS;
import static com.bphc.cryptopoll.app.Constants.GET_BLOCK_VOTES;
import static com.bphc.cryptopoll.app.Constants.GET_POLLS;
import static com.bphc.cryptopoll.app.Constants.LOGIN;
import static com.bphc.cryptopoll.app.Constants.ZKP_FIRST;
import static com.bphc.cryptopoll.app.Constants.ZKP_SECOND;

import com.bphc.cryptopoll.models.BlockResponse;
import com.bphc.cryptopoll.models.LoginResponse;
import com.bphc.cryptopoll.models.Poll;
import com.bphc.cryptopoll.models.PollResponse;
import com.bphc.cryptopoll.models.Vote;
import com.bphc.cryptopoll.models.VoteResponse;
import com.bphc.cryptopoll.models.ZKP;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Webservices {

    @POST(LOGIN)
    Call<LoginResponse> authWithGoogle(@Body HashMap<String, String> map);

    @POST(CREATE_POLL)
    Call<PollResponse> createPoll(@Header("Authorization") String access_token, @Body HashMap<String, Object> map);

    @GET(GET_POLLS)
    Call<PollResponse> getPolls(@Header("Authorization") String access_token);

    @POST(CAST_VOTE)
    Call<Vote> castVote(@Header("Authorization") String access_token, @Body HashMap<String, Integer> map);

    @GET(GET_BLOCKS)
    Call<BlockResponse> getBlocks(@Header("Authorization") String access_token);

    @GET(GET_BLOCK_VOTES)
    Call<VoteResponse> getBlockVotes(@Header("Authorization") String access_token, @Query("height") int height);

    @POST(ZKP_FIRST)
    Call<ZKP> zkpFirst(@Header("Authorization") String access_token, @Body HashMap<String, Integer> map);

    @POST(ZKP_SECOND)
    Call<ZKP> zkpSecond(@Header("Authorization") String access_token, @Body HashMap<String, Integer> map);


}
