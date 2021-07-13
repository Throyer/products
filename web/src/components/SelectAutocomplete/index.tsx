import { CircularProgress, TextField } from '@material-ui/core';
import { Autocomplete } from '@material-ui/lab';
import { FocusEvent, Fragment, useState } from 'react';
import { useDebouncedCallback } from 'use-debounce';

interface SelectProps<T> {
    name?: string;
    value?: T[];
    label?: string;
    initialData?: T[];
    extractLabelOptions: (option: T) => string;
    compare: (option: T, search: T) => boolean;
    onSearch?: (search?: string) => Promise<T[]>;
    onChange?: (option?: T[]) => void;
    onBlur?: (
        event: FocusEvent<HTMLInputElement | HTMLTextAreaElement>,
    ) => void;
    error?: boolean;
    helperText?: string;
    noOptionsText?: string;
    loadingText?: string;
    disabled?: boolean;
    defaultValue?: any;
}

function SelectAutocomplete<T>({
    name,
    value,
    label,
    initialData,
    extractLabelOptions,
    compare,
    onChange,
    onSearch,
    onBlur,
    error,
    helperText,
    noOptionsText,
    loadingText,
    disabled,
    defaultValue,
}: SelectProps<T>) {
    const [showOptions, setShowOptions] = useState(false);
    const [loading, setLoading] = useState(false);
    const [options, setOptions] = useState<T[]>(initialData ?? []);

    const handleOnChange = (option: T[]) => {
        if (onChange) {
            onChange(option);
        }
    };

    const onSearchDebounce = useDebouncedCallback(async (name?: string) => {
        try {
            if (onSearch && name) {
                const options = await onSearch(name);
                setOptions(options);
            }
        } finally {
            setLoading(false);
        }
    }, 1000);

    return (
        <Autocomplete
            open={showOptions}
            loadingText={loadingText ?? 'Carregando ...'}
            onOpen={() => {
                setShowOptions(true);
            }}
            onClose={() => {
                setShowOptions(false);
            }}
            multiple
            defaultValue={defaultValue}
            getOptionSelected={(option, search) => compare(option, search)}
            noOptionsText={noOptionsText ?? 'Nenhum resultado.'}
            onChange={(_event, option) => handleOnChange(option)}
            getOptionLabel={extractLabelOptions}
            options={options}
            loading={loading}
            disabled={disabled}
            renderInput={(params: any) => (
                <TextField
                    {...params}
                    name={name}
                    error={error}
                    helperText={helperText}
                    label={label}
                    variant="outlined"
                    size="small"
                    onBlur={onBlur}                    
                    onChange={({ target: { value } }) => {
                        setLoading(true);
                        onSearchDebounce(value.trim());
                    }}
                    InputProps={{
                        ...params.InputProps,
                        endAdornment: (
                            <Fragment>
                                {loading ? (
                                    <CircularProgress
                                        color="inherit"
                                        size={20}
                                    />
                                ) : null}
                                {params.InputProps.endAdornment}
                            </Fragment>
                        ),
                    }}
                />
            )}
        />
    );
}

export default SelectAutocomplete;
