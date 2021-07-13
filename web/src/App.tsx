import { useCallback, useEffect, useState } from "react";
import { Chip, Container, Grid, TextField } from "@material-ui/core";
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

import SelectAutocomplete from "./components/SelectAutocomplete";

import Products, { Product, SearchProducts } from "./service/products";
import Technologies, { SearchTechnologies, Technology } from "./service/technologies";

export const App = () => {
  const [products, setProducts] = useState<Product[]>([])

  const [name, setName] = useState<string>('')
  const [technologies, setTechnologies] = useState<Technology[]>([])

  const compare = useCallback((a: Technology, b: Technology) =>
    a && b && a.name === b.name, []);

  const searchTechnologies = useCallback(async (search?: SearchTechnologies) => {
    const { data: { content } } = await Technologies.index(search);
    return content;
  }, [])

  const searchProducts = useCallback(async (search?: SearchProducts) => {
    const { data: { content } } = await Products.index(search);
    return content;
  }, [])

  const handleChangeTechnologies = useCallback(async (technologies: Technology[] = []) => {
    setProducts(await searchProducts({
      name,
      technologies: technologies.map(({ name }) => name),
    }))
    setTechnologies(technologies)
  }, [name, searchProducts])

  const handleChangeName = useCallback(async (name: string = '') => {
    setProducts(await searchProducts({
      name,
      technologies: technologies.map(({ name }) => name),
    }))
    setName(name)
  }, [technologies, searchProducts])

  useEffect(() => {
    (async () => {
      setProducts(await searchProducts());
    })();
  }, [searchProducts])

  return (
    <Container style={{ marginTop: 120 }}>
      <Grid container spacing={3} style={{ marginBottom: 15 }}>
        <Grid item sm={6}>
          <TextField
            size="small"
            fullWidth
            label="Nome"
            variant="outlined"
            onChange={({ target: { value } }) => handleChangeName(value)}
          />
        </Grid>
        <Grid item sm={6}>
          <SelectAutocomplete
            label={'Tecnologias'}
            extractLabelOptions={({ name }) => name}
            compare={compare}
            onSearch={(name) => searchTechnologies({ name })}
            onChange={handleChangeTechnologies}
          />
        </Grid>
      </Grid>
      <TableContainer component={Paper}>
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell>Nome</TableCell>
              <TableCell>Descrição</TableCell>
              <TableCell>Mercado alvo</TableCell>
              <TableCell>Tecnologias</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {products.map(({ id, name, description, targetMarket, technologies }) => (
              <TableRow key={id}>
                <TableCell component="th" scope="row">
                  {name}
                </TableCell>
                <TableCell>{description}</TableCell>
                <TableCell>{targetMarket}</TableCell>
                <TableCell>
                  <Grid container spacing={2}>
                    {technologies.map(({ id, name }) => (
                      <Grid key={id} item>
                        <Chip size="small" label={name} />
                      </Grid>
                    ))}
                  </Grid>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Container>
  );
}
