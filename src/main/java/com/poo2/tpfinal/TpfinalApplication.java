package com.poo2.tpfinal;

import com.poo2.tpfinal.util.EvenementFactory;
import com.poo2.tpfinal.util.EvenementGatewayStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class TpfinalApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TpfinalApplication.class, args);

		// Vérifiez que les stratégies sont bien chargées
		String[] strategyBeans = context.getBeanNamesForType(EvenementGatewayStrategy.class);
		System.out.println("Stratégies chargées: " + Arrays.toString(strategyBeans));

		// Vérifiez que les fabriques sont bien chargées
		String[] factoryBeans = context.getBeanNamesForType(EvenementFactory.class);
		System.out.println("Fabriques chargées: " + Arrays.toString(factoryBeans));
	}
}