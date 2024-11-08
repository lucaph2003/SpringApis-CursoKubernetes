package org.lpodesta.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos", url="localhost:8002")
public interface ICursoClient {
    @DeleteMapping("/delete-course-user/{userId}")
    void deleteCourseUserById(@PathVariable Long userId);
}
