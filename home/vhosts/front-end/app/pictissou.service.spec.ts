import { TestBed, inject } from '@angular/core/testing';

import { PictissouService } from './pictissou.service';

describe('PictissouService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PictissouService]
    });
  });

  it('should be created', inject([PictissouService], (service: PictissouService) => {
    expect(service).toBeTruthy();
  }));
});
