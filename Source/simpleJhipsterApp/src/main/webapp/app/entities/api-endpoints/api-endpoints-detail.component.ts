import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { ApiEndpoints } from './api-endpoints.model';
import { ApiEndpointsService } from './api-endpoints.service';

@Component({
    selector: 'jhi-api-endpoints-detail',
    templateUrl: './api-endpoints-detail.component.html'
})
export class ApiEndpointsDetailComponent implements OnInit, OnDestroy {

    apiEndpoints: ApiEndpoints;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private apiEndpointsService: ApiEndpointsService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['apiEndpoints']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.apiEndpointsService.find(id).subscribe(apiEndpoints => {
            this.apiEndpoints = apiEndpoints;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
