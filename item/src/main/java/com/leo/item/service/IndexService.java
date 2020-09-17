package com.leo.item.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author Liu
 */
public interface IndexService {
    Map<String, Object> getDetail() throws ExecutionException, InterruptedException;
}
