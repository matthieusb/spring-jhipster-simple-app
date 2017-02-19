package com.msb.app.web.rest;

import com.msb.app.SimpleJhipsterApp;

import com.msb.app.domain.ApiEndpoints;
import com.msb.app.repository.ApiEndpointsRepository;
import com.msb.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApiEndpointsResource REST controller.
 *
 * @see ApiEndpointsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleJhipsterApp.class)
public class ApiEndpointsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENVIRONMENT = "AAAAAAAAAA";
    private static final String UPDATED_ENVIRONMENT = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private ApiEndpointsRepository apiEndpointsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restApiEndpointsMockMvc;

    private ApiEndpoints apiEndpoints;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ApiEndpointsResource apiEndpointsResource = new ApiEndpointsResource(apiEndpointsRepository);
        this.restApiEndpointsMockMvc = MockMvcBuilders.standaloneSetup(apiEndpointsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApiEndpoints createEntity() {
        ApiEndpoints apiEndpoints = new ApiEndpoints()
                .name(DEFAULT_NAME)
                .environment(DEFAULT_ENVIRONMENT)
                .host(DEFAULT_HOST)
                .path(DEFAULT_PATH);
        return apiEndpoints;
    }

    @Before
    public void initTest() {
        apiEndpointsRepository.deleteAll();
        apiEndpoints = createEntity();
    }

    @Test
    public void createApiEndpoints() throws Exception {
        int databaseSizeBeforeCreate = apiEndpointsRepository.findAll().size();

        // Create the ApiEndpoints

        restApiEndpointsMockMvc.perform(post("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiEndpoints)))
            .andExpect(status().isCreated());

        // Validate the ApiEndpoints in the database
        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeCreate + 1);
        ApiEndpoints testApiEndpoints = apiEndpointsList.get(apiEndpointsList.size() - 1);
        assertThat(testApiEndpoints.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiEndpoints.getEnvironment()).isEqualTo(DEFAULT_ENVIRONMENT);
        assertThat(testApiEndpoints.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testApiEndpoints.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    public void createApiEndpointsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiEndpointsRepository.findAll().size();

        // Create the ApiEndpoints with an existing ID
        ApiEndpoints existingApiEndpoints = new ApiEndpoints();
        existingApiEndpoints.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiEndpointsMockMvc.perform(post("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingApiEndpoints)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiEndpointsRepository.findAll().size();
        // set the field null
        apiEndpoints.setName(null);

        // Create the ApiEndpoints, which fails.

        restApiEndpointsMockMvc.perform(post("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiEndpoints)))
            .andExpect(status().isBadRequest());

        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEnvironmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiEndpointsRepository.findAll().size();
        // set the field null
        apiEndpoints.setEnvironment(null);

        // Create the ApiEndpoints, which fails.

        restApiEndpointsMockMvc.perform(post("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiEndpoints)))
            .andExpect(status().isBadRequest());

        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkHostIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiEndpointsRepository.findAll().size();
        // set the field null
        apiEndpoints.setHost(null);

        // Create the ApiEndpoints, which fails.

        restApiEndpointsMockMvc.perform(post("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiEndpoints)))
            .andExpect(status().isBadRequest());

        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiEndpointsRepository.findAll().size();
        // set the field null
        apiEndpoints.setPath(null);

        // Create the ApiEndpoints, which fails.

        restApiEndpointsMockMvc.perform(post("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiEndpoints)))
            .andExpect(status().isBadRequest());

        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllApiEndpoints() throws Exception {
        // Initialize the database
        apiEndpointsRepository.save(apiEndpoints);

        // Get all the apiEndpointsList
        restApiEndpointsMockMvc.perform(get("/api/api-endpoints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiEndpoints.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].environment").value(hasItem(DEFAULT_ENVIRONMENT.toString())))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }

    @Test
    public void getApiEndpoints() throws Exception {
        // Initialize the database
        apiEndpointsRepository.save(apiEndpoints);

        // Get the apiEndpoints
        restApiEndpointsMockMvc.perform(get("/api/api-endpoints/{id}", apiEndpoints.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apiEndpoints.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.environment").value(DEFAULT_ENVIRONMENT.toString()))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    public void getNonExistingApiEndpoints() throws Exception {
        // Get the apiEndpoints
        restApiEndpointsMockMvc.perform(get("/api/api-endpoints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateApiEndpoints() throws Exception {
        // Initialize the database
        apiEndpointsRepository.save(apiEndpoints);
        int databaseSizeBeforeUpdate = apiEndpointsRepository.findAll().size();

        // Update the apiEndpoints
        ApiEndpoints updatedApiEndpoints = apiEndpointsRepository.findOne(apiEndpoints.getId());
        updatedApiEndpoints
                .name(UPDATED_NAME)
                .environment(UPDATED_ENVIRONMENT)
                .host(UPDATED_HOST)
                .path(UPDATED_PATH);

        restApiEndpointsMockMvc.perform(put("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApiEndpoints)))
            .andExpect(status().isOk());

        // Validate the ApiEndpoints in the database
        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeUpdate);
        ApiEndpoints testApiEndpoints = apiEndpointsList.get(apiEndpointsList.size() - 1);
        assertThat(testApiEndpoints.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiEndpoints.getEnvironment()).isEqualTo(UPDATED_ENVIRONMENT);
        assertThat(testApiEndpoints.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testApiEndpoints.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    public void updateNonExistingApiEndpoints() throws Exception {
        int databaseSizeBeforeUpdate = apiEndpointsRepository.findAll().size();

        // Create the ApiEndpoints

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApiEndpointsMockMvc.perform(put("/api/api-endpoints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiEndpoints)))
            .andExpect(status().isCreated());

        // Validate the ApiEndpoints in the database
        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteApiEndpoints() throws Exception {
        // Initialize the database
        apiEndpointsRepository.save(apiEndpoints);
        int databaseSizeBeforeDelete = apiEndpointsRepository.findAll().size();

        // Get the apiEndpoints
        restApiEndpointsMockMvc.perform(delete("/api/api-endpoints/{id}", apiEndpoints.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApiEndpoints> apiEndpointsList = apiEndpointsRepository.findAll();
        assertThat(apiEndpointsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiEndpoints.class);
    }
}
