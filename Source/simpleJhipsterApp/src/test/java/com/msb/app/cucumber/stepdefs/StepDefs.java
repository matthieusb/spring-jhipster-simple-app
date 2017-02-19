package com.msb.app.cucumber.stepdefs;

import com.msb.app.SimpleJhipsterApp;

import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

@WebAppConfiguration
@ContextConfiguration(classes = SimpleJhipsterApp.class, loader = SpringApplicationContextLoader.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
