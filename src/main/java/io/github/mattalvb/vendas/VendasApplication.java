package io.github.mattalvb.vendas;

import io.github.mattalvb.vendas.domain.entity.Cliente;
import io.github.mattalvb.vendas.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes) {
		return args -> {
			System.out.println("Salvando Clientes");
			clientes.salvar(new Cliente("Matt"));
			clientes.salvar(new Cliente("Outro Cliente"));

			List<Cliente> todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);

			System.out.println("Atualizando clientes");
			todosClientes.forEach(c -> {
				c.setNome(c.getNome() + " atualizado." );
				clientes.atualizar(c);
			});

			todosClientes = clientes.obterTodos();
			todosClientes.forEach(System.out::println);

			System.out.println("Buscando Clientes");
			clientes.buscarPorNome("Cli").forEach(System.out::println);

			System.out.println("Deletando clientes");
			clientes.obterTodos().forEach(c -> {
				clientes.deletar(c);
			});

			todosClientes = clientes.obterTodos();
			if (todosClientes.isEmpty()) {
				System.out.println("Nenhum cliente encontrado");
			} else {
				todosClientes.forEach(System.out::println);
			}
		};
	}

	public static void main(String[] args){
		SpringApplication.run(VendasApplication.class, args);
	}
}
