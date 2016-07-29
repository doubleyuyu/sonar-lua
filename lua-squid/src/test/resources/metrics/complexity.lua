function insert (index, value)
      if not statetab[index] then
        statetab[index] = {value}
      else
        table.insert(statetab[index], value)
      end
    end