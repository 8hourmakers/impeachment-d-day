package com.impeachmenteightdday.impeachmenteightdday.server;


import android.util.Base64;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator implements NetDefine {

	private static String token = "";
	private static HttpLoggingInterceptor logging;

	private static okhttp3.OkHttpClient httpClient = new okhttp3.OkHttpClient.Builder().build();
	private static Retrofit.Builder builder =
			new Retrofit.Builder()
					.baseUrl( BASIC_PATH )
					.addConverterFactory( GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-mm-dd HH:mm:ss").create()));


	private static boolean isFile = false;

	public static <S> S createService( Class<S> serviceClass ) {

		if ( logging == null ) {
			logging = new HttpLoggingInterceptor();
			logging.setLevel( HttpLoggingInterceptor.Level.BODY );
		}
		isFile = false;
		return createService( serviceClass, token );
	}

	public static <S> S createService( Class<S> serviceClass, boolean bool ) {

		if ( logging == null ) {
			logging = new HttpLoggingInterceptor();
			logging.setLevel( HttpLoggingInterceptor.Level.BODY );
		}
		isFile = bool;
		return createService( serviceClass, token );
	}

	public static void setToken( String newToken ) {

		token = newToken;
	}

	public static <S> S createService( Class<S> serviceClass, final String authToken ) {

		if ( authToken != null ) {
			httpClient = new okhttp3.OkHttpClient.Builder()
					.addInterceptor( logging )
					.addInterceptor( new okhttp3.Interceptor() {

						@Override
						public okhttp3.Response intercept( Chain chain ) throws IOException {

							okhttp3.Request original = chain.request();

							// Request customization: add request headers
							okhttp3.Request.Builder requestBuilder = original.newBuilder()
									.header( "Content-Type", "application/json" )
									.method( original.method(), original.body() );
							if ( authToken.length() != 0 ) {
								requestBuilder = original.newBuilder()
										.header( "Authorization", "JWT " + authToken )
										.header( "Content-Type", "application/json" )
										.method( original.method(), original.body() );
							}
							if ( isFile ) {
								requestBuilder.header( "Content-Type", "multipart/form-data" );
							}
							Log.e( "token", authToken );
							okhttp3.Request request = requestBuilder.build();
							try {
								Log.e( "header", request.header( "Authorization" ) );
							}
							catch ( Exception e ) {

							}
							return chain.proceed( request );
						}
					} ).build();
		}

		Retrofit retrofit = builder.client( httpClient ).build();
		return retrofit.create( serviceClass );
	}


	public static <S> S createService( Class<S> serviceClass, String username, String password ) {

		if ( username != null && password != null ) {
			String credentials = username + ":" + password;
			final String basic =
					"Basic " + Base64.encodeToString( credentials.getBytes(), Base64.NO_WRAP );
			httpClient.interceptors().clear();
			httpClient.interceptors().add( new okhttp3.Interceptor() {

				@Override
				public okhttp3.Response intercept( Chain chain ) throws IOException {

					okhttp3.Request original = chain.request();

					okhttp3.Request.Builder requestBuilder = original.newBuilder()
							.header( "Authorization", basic )
							.header( "Accept", "applicaton/json" )
							.method( original.method(), original.body() );

					okhttp3.Request request = requestBuilder.build();
					return chain.proceed( request );
				}
			} );
		}

		Retrofit retrofit = builder.client( httpClient ).build();
		return retrofit.create( serviceClass );
	}
}