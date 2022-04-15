enum Operator {
  OR,
  AND,
}

interface Filter {
  key: string;
  operator: string;
  value: string | number;
}

interface FilterOptions {
  operator: Operator;
  filters: (FilterOptions | Filter)[];
}

const filter: FilterOptions = {
  operator: Operator.OR,
  filters: [
    {
      operator: Operator.AND,
      filters: [
        { key: "tenant", operator: "EQUALITY", value: 1 },
        {
          operator: Operator.AND,
          filters: [
            { key: "status", operator: "EQUALITY", value: "active" },
            {
              operator: Operator.AND,
              filters: [
                { key: "age", operator: "GREATER_THAN", value: 22 },
                { key: "firstName", operator: "LIKE", value: "allan" },
                {
                  operator: Operator.AND,
                  filters: [
                    { key: "age", operator: "LESS_THAN", value: 20 },
                    {
                      operator: Operator.OR,
                      filters: [
                        {
                          key: "firstName",
                          operator: "EQUALITY",
                          value: "tom",
                        },
                        {
                          key: "firstName",
                          operator: "EQUALITY",
                          value: "john",
                        },
                      ],
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],
    },
  ],
};
