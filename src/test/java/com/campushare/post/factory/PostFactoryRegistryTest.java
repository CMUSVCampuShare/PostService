package com.campushare.post.factory;

import com.campushare.post.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PostFactoryRegistryTest {

    private PostFactoryRegistry postFactoryRegistry;

    @Mock
    private RidePostFactory ridePostFactory;

    @Mock
    private LunchPostFactory lunchPostFactory;

    @Mock
    private FoodPickupPostFactory foodPickupPostFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Map<Type, PostFactory> factoryMap = new HashMap<>();

        ridePostFactory = Mockito.mock(RidePostFactory.class);
        when(ridePostFactory.getType()).thenReturn(Type.RIDE);
        factoryMap.put(ridePostFactory.getType(), ridePostFactory);

        lunchPostFactory = Mockito.mock(LunchPostFactory.class);
        when(lunchPostFactory.getType()).thenReturn(Type.LUNCH);
        factoryMap.put(lunchPostFactory.getType(), lunchPostFactory);

        foodPickupPostFactory = Mockito.mock(FoodPickupPostFactory.class);
        when(foodPickupPostFactory.getType()).thenReturn(Type.FOODPICKUP);
        factoryMap.put(foodPickupPostFactory.getType(), foodPickupPostFactory);

        postFactoryRegistry = new PostFactoryRegistry(new ArrayList<>(factoryMap.values()));
    }

    @Test
    void getPostFactory_ReturnsCorrectFactory() {
        when(ridePostFactory.getType()).thenReturn(Type.RIDE);
        when(lunchPostFactory.getType()).thenReturn(Type.LUNCH);
        when(foodPickupPostFactory.getType()).thenReturn(Type.FOODPICKUP);

        assertEquals(ridePostFactory, postFactoryRegistry.getPostFactory(Type.RIDE));
        assertEquals(lunchPostFactory, postFactoryRegistry.getPostFactory(Type.LUNCH));
        assertEquals(foodPickupPostFactory, postFactoryRegistry.getPostFactory(Type.FOODPICKUP));
    }
}

