import { ProductImage } from "./product.image";
export interface Product {
  productId: number;
  name: string;
  price: number;
  thumbnail: string;
  description: string;
  category_id: number;
  url: string;
  product_images: ProductImage[];
  operating_system?: string; // Optional, based on backend field
  screen_size?: number; // BigDecimal should be converted to number in JS/TS
  battery_capacity?: number;
  ram?: number;
  rom?: number;
  front_camera?: string;
  main_camera?: string;
  color?: string;
  release_date?: number; // Year
  in_stock?: number;
  brand_id?: number;
}

export function mapJsonToProduct(data: any): Product {
  return {
    productId: data.productId, // Map productId to id
    name: data.name,
    price: data.price,
    thumbnail: data.thumbnail,
    description: data.description,
    category_id: data.brand?.id, // Assuming you want to use brand_id as category_id
    url: data.thumbnail, // Or generate a URL if needed
    product_images: data.productImages.map((img: any) => ({
      id: img.id,
      imageUrl: img.imageUrl
    })),
    operating_system: data.operatingSystem,
    screen_size: data.screenSize,
    battery_capacity: data.batteryCapacity,
    ram: data.ram,
    rom: data.rom,
    front_camera: data.frontCamera,
    main_camera: data.mainCamera,
    color: data.color,
    release_date: data.releaseDate,
    in_stock: data.inStock,
    brand_id: data.brand?.id
  };
}