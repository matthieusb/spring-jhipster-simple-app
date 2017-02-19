package com.msb.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.msb.app.domain.ApiEndpoints;

import com.msb.app.repository.ApiEndpointsRepository;
import com.msb.app.web.rest.util.HeaderUtil;
import com.msb.app.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApiEndpoints.
 */
@RestController
@RequestMapping("/api")
public class ApiEndpointsResource {

    private final Logger log = LoggerFactory.getLogger(ApiEndpointsResource.class);

    private static final String ENTITY_NAME = "apiEndpoints";
        
    private final ApiEndpointsRepository apiEndpointsRepository;

    public ApiEndpointsResource(ApiEndpointsRepository apiEndpointsRepository) {
        this.apiEndpointsRepository = apiEndpointsRepository;
    }

    /**
     * POST  /api-endpoints : Create a new apiEndpoints.
     *
     * @param apiEndpoints the apiEndpoints to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiEndpoints, or with status 400 (Bad Request) if the apiEndpoints has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-endpoints")
    @Timed
    public ResponseEntity<ApiEndpoints> createApiEndpoints(@Valid @RequestBody ApiEndpoints apiEndpoints) throws URISyntaxException {
        log.debug("REST request to save ApiEndpoints : {}", apiEndpoints);
        if (apiEndpoints.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new apiEndpoints cannot already have an ID")).body(null);
        }
        ApiEndpoints result = apiEndpointsRepository.save(apiEndpoints);
        return ResponseEntity.created(new URI("/api/api-endpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-endpoints : Updates an existing apiEndpoints.
     *
     * @param apiEndpoints the apiEndpoints to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiEndpoints,
     * or with status 400 (Bad Request) if the apiEndpoints is not valid,
     * or with status 500 (Internal Server Error) if the apiEndpoints couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-endpoints")
    @Timed
    public ResponseEntity<ApiEndpoints> updateApiEndpoints(@Valid @RequestBody ApiEndpoints apiEndpoints) throws URISyntaxException {
        log.debug("REST request to update ApiEndpoints : {}", apiEndpoints);
        if (apiEndpoints.getId() == null) {
            return createApiEndpoints(apiEndpoints);
        }
        ApiEndpoints result = apiEndpointsRepository.save(apiEndpoints);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apiEndpoints.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-endpoints : get all the apiEndpoints.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of apiEndpoints in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/api-endpoints")
    @Timed
    public ResponseEntity<List<ApiEndpoints>> getAllApiEndpoints(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ApiEndpoints");
        Page<ApiEndpoints> page = apiEndpointsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/api-endpoints");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /api-endpoints/:id : get the "id" apiEndpoints.
     *
     * @param id the id of the apiEndpoints to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiEndpoints, or with status 404 (Not Found)
     */
    @GetMapping("/api-endpoints/{id}")
    @Timed
    public ResponseEntity<ApiEndpoints> getApiEndpoints(@PathVariable String id) {
        log.debug("REST request to get ApiEndpoints : {}", id);
        ApiEndpoints apiEndpoints = apiEndpointsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(apiEndpoints));
    }

    /**
     * DELETE  /api-endpoints/:id : delete the "id" apiEndpoints.
     *
     * @param id the id of the apiEndpoints to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-endpoints/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiEndpoints(@PathVariable String id) {
        log.debug("REST request to delete ApiEndpoints : {}", id);
        apiEndpointsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
