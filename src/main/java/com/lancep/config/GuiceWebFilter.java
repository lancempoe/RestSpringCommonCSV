package com.lancep.config;

import com.google.inject.servlet.GuiceFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/api/*")
public class GuiceWebFilter extends GuiceFilter {
}
