package com.luv2code.imageuploader.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.repository.PackageRepository;
import com.luv2code.imageuploader.service.impl.PackageServiceImpl;

/**
 * Created by lzugaj on Monday, March 2020
 */

@SpringBootTest
public class PackageServiceImplTest {

    @Mock
    public PackageRepository packageRepository;

    @InjectMocks
    public PackageServiceImpl packageService;

    @BeforeEach
    public void setUp() {
         MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Long packageId = 1L;
        Package aPackage = new Package(packageId, "FREE", 5, 10, null, null);

        when(packageRepository.findById(aPackage.getId())).thenReturn(java.util.Optional.of(aPackage));

        Package searchedPackage = packageService.findById(aPackage.getId());

        assertNotNull(searchedPackage);
        assertEquals("1", searchedPackage.getId().toString());
        assertEquals("FREE", searchedPackage.getName());
        assertEquals("5", searchedPackage.getUploadSize().toString());
        assertEquals("10", searchedPackage.getDailyUploadLimit().toString());
    }

    @Test
    public void testFindByIdNullPointerException() {
        Long packageId = 1L;
        Package aPackage = new Package(packageId, "FREE", 5, 10, null, null);

        when(packageRepository.findById(aPackage.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> packageService.findById(aPackage.getId()));
    }

    @Test
    public void testFindAll() {
        Long firstPackageId = 1L;
        Package firstPackage = new Package(firstPackageId, "FREE", 5, 10, null, null);

        Long secondPackageId = 2L;
        Package secondPackage = new Package(secondPackageId, "PRO", 15, 22, null, null);

        Long thirdPackageId = 3L;
        Package thirdPackage = new Package(thirdPackageId, "GOLD", 25, 30, null, null);

        List<Package> packages = new ArrayList<>();
        packages.add(firstPackage);
        packages.add(secondPackage);
        packages.add(thirdPackage);

        when(packageRepository.findAll()).thenReturn(packages);

        List<Package> searchedPackages = packageService.findAll();

        assertEquals(3, packages.size());
        assertEquals(3, searchedPackages.size());
        verify(packageRepository, times(1)).findAll();
    }
}
