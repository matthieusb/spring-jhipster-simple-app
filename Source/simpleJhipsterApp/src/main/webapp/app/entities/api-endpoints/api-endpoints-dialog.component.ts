import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ApiEndpoints } from './api-endpoints.model';
import { ApiEndpointsPopupService } from './api-endpoints-popup.service';
import { ApiEndpointsService } from './api-endpoints.service';
@Component({
    selector: 'jhi-api-endpoints-dialog',
    templateUrl: './api-endpoints-dialog.component.html'
})
export class ApiEndpointsDialogComponent implements OnInit {

    apiEndpoints: ApiEndpoints;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private apiEndpointsService: ApiEndpointsService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['apiEndpoints']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.apiEndpoints.id !== undefined) {
            this.apiEndpointsService.update(this.apiEndpoints)
                .subscribe((res: ApiEndpoints) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.apiEndpointsService.create(this.apiEndpoints)
                .subscribe((res: ApiEndpoints) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: ApiEndpoints) {
        this.eventManager.broadcast({ name: 'apiEndpointsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-api-endpoints-popup',
    template: ''
})
export class ApiEndpointsPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private apiEndpointsPopupService: ApiEndpointsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.apiEndpointsPopupService
                    .open(ApiEndpointsDialogComponent, params['id']);
            } else {
                this.modalRef = this.apiEndpointsPopupService
                    .open(ApiEndpointsDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
