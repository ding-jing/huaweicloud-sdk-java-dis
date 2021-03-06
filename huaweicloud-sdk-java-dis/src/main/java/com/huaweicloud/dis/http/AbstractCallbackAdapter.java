package com.huaweicloud.dis.http;

import com.huaweicloud.dis.core.handler.AsyncHandler;

public abstract class AbstractCallbackAdapter<InnerT, T> implements AsyncHandler<InnerT>{
	private AsyncHandler<T> innerAsyncHandler;
	
	private AbstractFutureAdapter<T, InnerT> futureAdapter;
	
	public AbstractCallbackAdapter(AsyncHandler<T> innerAsyncHandler) {
		this.innerAsyncHandler = innerAsyncHandler;
	}

	public AbstractCallbackAdapter(AsyncHandler<T> innerAsyncHandler, AbstractFutureAdapter<T, InnerT> futureAdapter) {
		this.innerAsyncHandler = innerAsyncHandler;
		this.futureAdapter = futureAdapter;
	}
	
	@Override
	public void onError(Exception exception) {
		innerAsyncHandler.onError(exception);
	}

	@Override
	public void onSuccess(InnerT result) {
		T t = null;
		if(futureAdapter != null) {
			t = futureAdapter.getT(result);
		}else {
			t = toInnerT(result);
		}
		innerAsyncHandler.onSuccess(t);
	}

	protected abstract T toInnerT(InnerT result);
	
}
