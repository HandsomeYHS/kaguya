package com.akanemurakawa.kaguya.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "${kaguya.provider.service.name}", url = "${kaguya.provider.service.url}")
public interface GalleryServiceRemote extends GalleryService {
}
