import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ApiEndpoints } from './api-endpoints.model';
import { ApiEndpointsPopupService } from './api-endpoints-popup.service';
import { ApiEndpointsService } from './api-endpoints.service';

@Component({
    selector: 'jhi-api-endpoints-delete-dialog',
    templateUrl: './api-endpoints-delete-dialog.component.html'
})
export class ApiEndpointsDeleteDialogComponent {

    apiEndpoints: ApiEndpoints;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private apiEndpointsService: ApiEndpointsService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['apiEndpoints']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.apiEndpointsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'apiEndpointsListModification',
                content: 'Deleted an apiEndpoints'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-api-endpoints-delete-popup',
    template: ''
})
export class ApiEndpointsDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private apiEndpointsPopupService: ApiEndpointsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.apiEndpointsPopupService
                .open(ApiEndpointsDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
