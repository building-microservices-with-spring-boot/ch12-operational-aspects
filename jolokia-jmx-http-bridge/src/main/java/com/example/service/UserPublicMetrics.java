package com.example.service;

import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserPublicMetrics implements PublicMetrics {

    @Autowired
    UserRepository userRepository;


    @Override
    public Collection<Metric<?>> metrics() {
        long userCount = this.userRepository.count();

        Set<Metric<?>> metrics = new HashSet<>();
        metrics.add(new Metric<>("users.count", userCount));
        return metrics;
    }
}
